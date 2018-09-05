package com.ranhfun.soup.snack.select;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.OptionGroupModel;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.internal.OptionGroupModelImpl;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.util.AbstractSelectModel;

/**
 * @author jued
 * 
 * @param <T>
 */
public class DisplayableSelectionModel<T extends IDisplayable> extends
                AbstractSelectModel {

        private List<T> list;

        private String groupLabel = null;

        private String instructions = null;

        public DisplayableSelectionModel(List<T> list) {
                this.list = list;
        }

        public DisplayableSelectionModel(List<T> list, String groupLabelIn) {
                this.list = list;
                this.groupLabel = groupLabelIn;
        }

        public DisplayableSelectionModel(List<T> list, String groupLabelIn,
                        String instructionsIn) {
                this.list = list;
                this.groupLabel = groupLabelIn;
                this.instructions = instructionsIn;
        }

        private List<OptionModel> generateOptionModelList() {
                List<OptionModel> optionModelList = new ArrayList<OptionModel>();
                if (list != null) {
                        for (T obj : list) {
                                optionModelList.add(new OptionModelImpl(obj.toDisplayLabel(), obj));
                        }
                }
                return optionModelList;
        }

        private List<OptionModel> generateEmptyOptionModelList() {
                List<OptionModel> l = new ArrayList<OptionModel>();
                l.add(new OptionModelImpl(instructions, null));
                return l;
        }

        public List<OptionGroupModel> getOptionGroups() {
                if (groupLabel == null) {
                        return null;
                } else {
                        List<OptionGroupModel> optionModelList = new ArrayList<OptionGroupModel>();
                        if (this.instructions != null) {
                                // append a group for the sole purpose of having the instruction
                                // item appear before other groups, and not inside one
                                // particular group.
                                optionModelList.add(new OptionGroupModelImpl("Instructions",
                                                false, generateEmptyOptionModelList()));
                        }
                        optionModelList.add(new OptionGroupModelImpl(this.groupLabel,
                                        false, generateOptionModelList()));

                        return optionModelList;
                }
        }

        public List<OptionModel> getOptions() {
                if (groupLabel == null) {
                        List<OptionModel> l = generateOptionModelList();
                        if (this.instructions != null) {
                                // append the instructions as a null item.
                                l.add(0, new OptionModelImpl(instructions, null));
                        }
                        return l;
                } else {
                        return null;
                }
        }
}

