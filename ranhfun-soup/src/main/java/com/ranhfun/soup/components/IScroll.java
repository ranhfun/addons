package com.ranhfun.soup.components;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.apache.tapestry5.Binding;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentAction;
import org.apache.tapestry5.ComponentParameterConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.PropertyOverrides;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.RequestParameter;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.beaneditor.PropertyModel;
import org.apache.tapestry5.corelib.components.Any;
import org.apache.tapestry5.grid.ColumnSort;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.grid.GridModel;
import org.apache.tapestry5.grid.GridSortModel;
import org.apache.tapestry5.grid.SortConstraint;
import org.apache.tapestry5.internal.TapestryInternalUtils;
import org.apache.tapestry5.internal.bindings.AbstractBinding;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.BeanModelSource;
import org.apache.tapestry5.services.ComponentDefaultProvider;
import org.apache.tapestry5.services.ComponentEventResultProcessor;
import org.apache.tapestry5.services.FormSupport;
import org.apache.tapestry5.services.javascript.InitializationPriority;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

import com.ranhfun.jquery.services.utils.JQueryUtils;

public class IScroll implements ClientElement, GridModel {

	public static final String ISCROLL_UPDATE = "iscrollupdate";
	
    /**
     * The source of data for the Grid to display. This will usually be a List or array but can also be an explicit
     * {@link GridDataSource}. For Lists and object arrays, a GridDataSource is created automatically as a wrapper
     * around the underlying List.
     */
    @Parameter(required = true, autoconnect = true)
    private GridDataSource source;

    /**
     * A wrapper around the provided GridDataSource that caches access to the availableRows property. This is the source
     * provided to sub-components.
     */
    private GridDataSource cachingSource;

    /**
     * The number of rows of data displayed on each page. If there are more rows than will fit, the Grid will divide up
     * the rows into "pages" and (normally) provide a pager to allow the user to navigate within the overall result
     * set.
     */
    @Parameter(BindingConstants.SYMBOL + ":" + ComponentParameterConstants.GRID_ROWS_PER_PAGE)
    private int rowsPerPage;	
	
    /**
     * Used to store the current object being rendered (for the current row). This is used when parameter blocks are
     * provided to override the default cell renderer for a particular column ... the components within the block can
     * use the property bound to the row parameter to know what they should render.
     */
    @Parameter(principal = true)
    private Object row;

    /**
     * The model used to identify the properties to be presented and the order of presentation. The model may be
     * omitted, in which case a default model is generated from the first object in the data source (this implies that
     * the objects provided by the source are uniform). The model may be explicitly specified to override the default
     * behavior, say to reorder or rename columns or add additional columns. The add, include,
     * exclude and reorder
     * parameters are <em>only</em> applied to a default model, not an explicitly provided one.
     */
    @Parameter
    private BeanModel model;

    /**
     * The model parameter after modification due to the add, include, exclude and reorder parameters.
     */
    private BeanModel dataModel;

    /**
     * The model used to handle sorting of the Grid. This is generally not specified, and the built-in model supports
     * only single column sorting. The sort constraints (the column that is sorted, and ascending vs. descending) is
     * stored as persistent fields of the Grid component.
     */
    @Parameter
    private GridSortModel sortModel;

    /**
     * A Block to render instead of the table (and pager, etc.) when the source is empty. The default is simply the text
     * "There is no data to display". This parameter is used to customize that message, possibly including components to
     * allow the user to create new objects.
     */
    //@Parameter(value = BindingConstants.SYMBOL + ":" + ComponentParameterConstants.GRID_EMPTY_BLOCK,
    @Parameter(value = "block:empty",
            defaultPrefix = BindingConstants.LITERAL)
    private Block empty;
    
    @Parameter(value = "block:rowBlock",
            defaultPrefix = BindingConstants.LITERAL, required = true, allowNull = false)
    private Block rowBlock;
    
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    @Property(write = false)
    private String wrapperClass;
    
    @Parameter(name = "class", defaultPrefix = BindingConstants.LITERAL)
    @Property(write = false)
    private String ulClass;
    
    @Component(parameters = "class=ulClass", inheritInformalParameters = true)
    private Any ul;
    
    @Parameter(value="false", defaultPrefix = BindingConstants.LITERAL)
    private boolean unwrapperFlag;
    
    /**
     * The activation context.
     */
    @Parameter
    private Object[] context;
	
    @Parameter(value = "prop:componentResources.id", defaultPrefix = BindingConstants.LITERAL)
    private String clientId;
	
    @Parameter
    private JSONObject params;
    
    /**
     * The Tapestry.Initializer method to call to initialize the dialog.
     */
    @Parameter("literal:iscroll")
    private String initMethod;
    
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String customFn;
    
    @Inject
    private JavaScriptSupport support;
    
    @Inject
    private AssetSource assetSource;
    
    @Inject
    private ComponentResources resources;
    
    /**
     * Set up via the traditional or Ajax component event request handler
     */
    @Environmental
    private ComponentEventResultProcessor componentEventResultProcessor;
    
    @Inject
    private ComponentDefaultProvider defaultsProvider;
    
    @Persist
    private Integer currentPage;
    
    @Persist
    private String sortColumnId;

    @Persist
    private Boolean sortAscending;

    @Inject
    private BeanModelSource modelSource;

    @Component(parameters =
    { "rowsPerPage=rowsPerPage", "currentPage=currentPage", "row=row", 
    		"rowBlock=rowBlock"}, publishParameters = "rowIndex,rowClass,volatile,encoder,lean")
    private IScrollRows rows;
    
    @Environmental(false)
    private FormSupport formSupport;

    @Parameter(value = "this", allowNull = false)
    @Property(write = false)
    private PropertyOverrides overrides;
    
    ValueEncoder defaultEncoder()
    {
        return defaultsProvider.defaultValueEncoder("row", resources);
    }

    public Object getRowBlock() {
    	return rowBlock;
    }
    
    public Object getEmpty() {
    	return empty;
    }
    
    public boolean getHasRows() {
    	return cachingSource.getAvailableRows()!=0;
    }
    
    @BeginRender
    void startDiv(MarkupWriter writer)
    {
    	if (!unwrapperFlag) {
            writer.element("div", "id", getClientId());
            writer.element("div", "class", wrapperClass);
		}
    }
    
    @AfterRender
    void declareDialog(MarkupWriter writer)
    {
        resources.renderInformalParameters(writer);
        if (!unwrapperFlag) {
	        writer.end();
	        writer.end();
        }

        Link link = resources.createEventLink(EventConstants.ACTION, context);
        
        Link refreshLink = resources.createEventLink(EventConstants.REFRESH, context);
        
        int availableRows = source.getAvailableRows();

        int maxPages = ((availableRows - 1) / rowsPerPage) + 1;
        
        JSONObject data = new JSONObject();
        data.put("id", getClientId());
        data.put("url", link.toAbsoluteURI());
        data.put("refreshLink", refreshLink.toAbsoluteURI());
        data.put("maxPages", maxPages);
        data.put("currentPage", getCurrentPage());
        data.put("totalRows", availableRows);
        data.put("unwrapperFlag", unwrapperFlag);
        if (customFn!=null) {
        	data.put("customFn", customFn);
		}

        if (params == null)
            params = new JSONObject();

        JSONObject defaults = new JSONObject();
        defaults.put("mouseWheel", true);

        JQueryUtils.merge(defaults, params);

        data.put("params", defaults);

        configure(data);

        support.addInitializerCall(initMethod, data);
        if (customFn!=null) {
			support.addScript(InitializationPriority.LATE, customFn);
		}
    }
    
    @AfterRender
    protected void addJSResources()
    {
        String[] scripts = { "com/ranhfun/soup/components/iscroll/iscroll.js", 
        		"com/ranhfun/soup/components/iscroll/jquery.iscroll.js"};
        for (String path : scripts)
        {
            support.importJavaScriptLibrary(assetSource.getClasspathAsset(path));
        }
    }

    void onAction(@RequestParameter(value = "page") int page) throws IOException
    {
    	setCurrentPage(page);
    	componentEventResultProcessor.processResultValue(this);
    }
    
    void onRefresh(@RequestParameter(value = "totalRows") int totalRows) throws IOException
    {
    	int availableRows = source.getAvailableRows();
    	if (availableRows!=totalRows) {
        	int maxPages = ((availableRows - 1) / rowsPerPage) + 1;
        	componentEventResultProcessor.processResultValue(new JSONObject().put("success", true).put("maxPages", maxPages).put("totalRows", availableRows));
		} else {
			componentEventResultProcessor.processResultValue(new JSONObject().put("success", false));
		}
    }
    
    protected void configure(JSONObject params)
    {
    }
    
	public String getClientId() {
		return this.clientId;
	}

    /**
     * A version of GridDataSource that caches the availableRows property. This addresses TAPESTRY-2245.
     */
    static class CachingDataSource implements GridDataSource
    {
        private final GridDataSource delegate;

        private boolean availableRowsCached;

        private int availableRows;

        CachingDataSource(GridDataSource delegate)
        {
            this.delegate = delegate;
        }

        CachingDataSource(GridDataSource delegate, int availableRows)
        {
            this.delegate = delegate;
            this.availableRows = availableRows;
        }
        
        public int getAvailableRows()
        {
            if (!availableRowsCached)
            {
                availableRows = delegate.getAvailableRows();
                availableRowsCached = true;
            }

            return availableRows;
        }

        public void prepare(int startIndex, int endIndex, List<SortConstraint> sortConstraints)
        {
            delegate.prepare(startIndex, endIndex, sortConstraints);
        }

        public Object getRowValue(int index)
        {
            return delegate.getRowValue(index);
        }

        public Class getRowType()
        {
            return delegate.getRowType();
        }
    }

    /**
     * Default implementation that only allows a single column to be the sort column, and stores the sort information as
     * persistent fields of the Grid component.
     */
    class DefaultGridSortModel implements GridSortModel
    {
        public ColumnSort getColumnSort(String columnId)
        {
            if (!TapestryInternalUtils.isEqual(columnId, sortColumnId))
                return ColumnSort.UNSORTED;

            return getColumnSort();
        }

        private ColumnSort getColumnSort()
        {
            return getSortAscending() ? ColumnSort.ASCENDING : ColumnSort.DESCENDING;
        }

        public void updateSort(String columnId)
        {
            assert InternalUtils.isNonBlank(columnId);
            if (columnId.equals(sortColumnId))
            {
                setSortAscending(!getSortAscending());
                return;
            }

            sortColumnId = columnId;
            setSortAscending(true);
        }

        public List<SortConstraint> getSortConstraints()
        {
            if (sortColumnId == null)
                return Collections.emptyList();

            PropertyModel sortModel = getDataModel().getById(sortColumnId);

            SortConstraint constraint = new SortConstraint(sortModel, getColumnSort());

            return Collections.singletonList(constraint);
        }

        public void clear()
        {
            sortColumnId = null;
        }
    }

    GridSortModel defaultSortModel()
    {
        return new DefaultGridSortModel();
    }

    /**
     * Returns a {@link org.apache.tapestry5.Binding} instance that attempts to identify the model from the source
     * parameter (via {@link org.apache.tapestry5.grid.GridDataSource#getRowType()}. Subclasses may override to provide
     * a different mechanism. The returning binding is variant (not invariant).
     * 
     * @see BeanModelSource#createDisplayModel(Class, org.apache.tapestry5.ioc.Messages)
     */
    protected Binding defaultModel()
    {
        return new AbstractBinding()
        {
            public Object get()
            {
                // Get the default row type from the data source

                GridDataSource gridDataSource = source;

                Class rowType = gridDataSource.getRowType();

                if (rowType == null)
                    throw new RuntimeException(
                            String.format(
                                    "Unable to determine the bean type for rows from %s. You should bind the model parameter explicitly.",
                                    gridDataSource));

                // Properties do not have to be read/write

                return modelSource.createDisplayModel(rowType, overrides.getOverrideMessages());
            }

            /**
             * Returns false. This may be overkill, but it basically exists because the model is
             * inherently mutable and therefore may contain client-specific state and needs to be
             * discarded at the end of the request. If the model were immutable, then we could leave
             * invariant as true.
             */
            public boolean isInvariant()
            {
                return false;
            }
        };
    }

    static final ComponentAction<IScroll> SETUP_DATA_SOURCE = new ComponentAction<IScroll>()
    {

		private static final long serialVersionUID = 1L;

		public void execute(IScroll component)
        {
            component.setupDataSource();
        }

        @Override
        public String toString()
        {
            return "IScroll.SetupDataSource";
        }
    };

    void setupRender()
    {
        if (formSupport != null)
            formSupport.store(this, SETUP_DATA_SOURCE);

        setupDataSource();

    }
    
    void setupDataSource()
    {
        // TAP5-34: We pass the source into the CachingDataSource now; previously
        // we were accessing source directly, but during submit the value wasn't
        // cached, and therefore access was very inefficient, and sorting was
        // very inconsistent during the processing of the form submission.

    	cachingSource = new CachingDataSource(source);

        int availableRows = cachingSource.getAvailableRows();

        if (availableRows == 0)
            return;

        int maxPage = ((availableRows - 1) / rowsPerPage) + 1;

        // This captures when the number of rows has decreased, typically due to deletions.

        int effectiveCurrentPage = getCurrentPage();

        if (effectiveCurrentPage > maxPage)
            effectiveCurrentPage = maxPage;

        int startIndex = (effectiveCurrentPage - 1) * rowsPerPage;

        int endIndex = Math.min(startIndex + rowsPerPage - 1, availableRows - 1);

        dataModel = null;

        cachingSource.prepare(startIndex, endIndex, sortModel.getSortConstraints());
    }

    public BeanModel getDataModel()
    {
        return model;
    }

    public GridDataSource getDataSource()
    {
        return cachingSource;
    }

    public GridSortModel getSortModel()
    {
        return sortModel;
    }

    public int getCurrentPage()
    {
    	return currentPage == null ? 1 : currentPage;
    }

    public void setCurrentPage(int currentPage)
    {
        this.currentPage = currentPage;
    }

    public int getRowsPerPage()
    {
        return rowsPerPage;
    }

    private boolean getSortAscending()
    {
        return sortAscending != null && sortAscending.booleanValue();
    }

    private void setSortAscending(boolean sortAscending)
    {
        this.sortAscending = sortAscending;
    }
    
    public Object getRow()
    {
        return row;
    }

    public void setRow(Object row)
    {
        this.row = row;
    }
	
    public void reset()
    {
        setCurrentPage(1);
        sortModel.clear();
    }
    
}
