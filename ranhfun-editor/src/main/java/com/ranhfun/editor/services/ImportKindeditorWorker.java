package com.ranhfun.editor.services;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.model.MutableComponentModel;
import org.apache.tapestry5.plastic.MethodAdvice;
import org.apache.tapestry5.plastic.MethodInvocation;
import org.apache.tapestry5.plastic.PlasticClass;
import org.apache.tapestry5.plastic.PlasticMethod;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.TransformConstants;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.apache.tapestry5.services.transform.ComponentClassTransformWorker2;
import org.apache.tapestry5.services.transform.TransformationSupport;

import com.ranhfun.editor.ImportKindeditor;

public class ImportKindeditorWorker implements ComponentClassTransformWorker2 {

	private final AssetSource assetSource;

	private final JavaScriptSupport javaScriptSupport;
	
	private final boolean productionMode;

	public ImportKindeditorWorker(AssetSource assetSource,

	JavaScriptSupport javaScriptSupport,

	@Symbol(SymbolConstants.PRODUCTION_MODE)
    boolean productionMode) {
		this.assetSource = assetSource;
		this.javaScriptSupport = javaScriptSupport;

		this.productionMode = productionMode;
	}

	public void transform(PlasticClass plasticClass,
			TransformationSupport support, MutableComponentModel model) {
		ImportKindeditor annotation = plasticClass.getAnnotation(ImportKindeditor.class);

		if (annotation == null)
			return;

		addAdvicetoBeginRender(plasticClass);

		model.addRenderPhase(BeginRender.class);
	}

	private void addAdvicetoBeginRender(PlasticClass plasticClass) {
		PlasticMethod method = plasticClass.introduceMethod(TransformConstants.BEGIN_RENDER_DESCRIPTION);

		method.addAdvice(createBeginRenderAdvice());
	}

	private MethodAdvice createBeginRenderAdvice() {

		return new MethodAdvice() {

			public void advise(MethodInvocation invocation) {
				final StringBuilder relativePath = new StringBuilder();
				if (productionMode) {
					relativePath.append("kindeditor/kindeditor-min.js");
				} else {
					relativePath.append("kindeditor/kindeditor.js");
				}
				javaScriptSupport.importJavaScriptLibrary(assetSource.getExpandedAsset("classpath:" + relativePath));

				invocation.proceed();				
			}
		};
	}
	
}
