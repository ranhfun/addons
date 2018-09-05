package com.ranhfun.soup.snack.select;

import java.util.List;

import org.apache.tapestry5.ValueEncoder;

public class DisplayableValueEncoder<T extends IDisplayable> implements
                ValueEncoder<T> {
        private List<T> list;

        public DisplayableValueEncoder(List<T> list) {
                this.list = list;
        }

        public String toClient(T obj) {
                if (obj == null) {
                        return null;
                } else {
                        return obj.toDisplayValue();
                }
        }

        public T toValue(String string) {
                if (list != null) {
                        for (T obj : list) {
                                if (obj.toDisplayValue().equals(string)) {
                                        return obj;
                                }
                        }
                }
                return null;
        }
}
