package com.ucab.ecotoolbox.ecotoolbox;

import java.util.ArrayList;

/**
 * Created by Toshiba PC on 12/21/2014.
 */
public class ParentItem {

        private String name;
        private String text1;
        private String text2;
        // ArrayList to store child objects
        private ArrayList<ChildItem> children;

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }
        public String getText1()
        {
            return text1;
        }

        public void setText1(String text1)
        {
            this.text1 = text1;
        }

        public String getText2()
        {
            return text2;
        }

        public void setText2(String text2)
        {
            this.text2 = text2;
        }

        // ArrayList to store child objects
        public ArrayList<ChildItem> getChildren()
        {
            return children;
        }

        public void setChildren(ArrayList<ChildItem> children)
        {
            this.children = children;
        }
}
