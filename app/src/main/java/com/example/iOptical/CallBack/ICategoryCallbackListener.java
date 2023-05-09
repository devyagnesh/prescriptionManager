package com.example.iOptical.CallBack;

import com.example.iOptical.Model.CategoryModel;

import java.util.List;

public interface ICategoryCallbackListener {
    void onCategoryLoadSuccess(List<CategoryModel> bestDealModels);
    void onCategoryLoadFailed(String message);
}
