package com.example.iOptical.ui.productlist;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.iOptical.Common.Common;
import com.example.iOptical.Model.ProductModel;

import java.util.List;

public class ProductListViewModel extends ViewModel {

    private MutableLiveData<List<ProductModel>> mutableLiveDataProductList;

    public ProductListViewModel() {

    }

    public MutableLiveData<List<ProductModel>> getMutableLiveDataProductList() {
        if(mutableLiveDataProductList==null)
            mutableLiveDataProductList = new MutableLiveData<>();
        mutableLiveDataProductList.setValue(Common.categorySelected.getProduct());
        return mutableLiveDataProductList;
    }
}