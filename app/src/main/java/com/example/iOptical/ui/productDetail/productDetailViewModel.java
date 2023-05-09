package com.example.iOptical.ui.productDetail;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.iOptical.Common.Common;
import com.example.iOptical.Model.ProductModel;

public class productDetailViewModel extends ViewModel {

    private MutableLiveData<ProductModel> mutableLiveDataProduct;

    public productDetailViewModel() {


    }

    public MutableLiveData<ProductModel> getMutableLiveDataProduct() {
        if(mutableLiveDataProduct == null)
        mutableLiveDataProduct = new MutableLiveData<>();
        mutableLiveDataProduct.setValue(Common.selectedProduct);


        return mutableLiveDataProduct;
    }
}