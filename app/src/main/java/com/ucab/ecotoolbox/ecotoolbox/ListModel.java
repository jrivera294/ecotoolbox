package com.ucab.ecotoolbox.ecotoolbox;

public class ListModel {

    private  String CompanyName="";
    private  String Image="";


    /*********** Set Methods ******************/

    public void setCompanyName(String CompanyName)
    {
        this.CompanyName = CompanyName;
    }

    public void setImage(String Image)
    {
        this.Image = Image;
    }

    /*********** Get Methods ****************/

    public String getCompanyName()
    {
        return this.CompanyName;
    }

    public String getImage()
    {
        return this.Image;
    }

}