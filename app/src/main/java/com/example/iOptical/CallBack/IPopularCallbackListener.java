package com.example.iOptical.CallBack;

import com.example.iOptical.Model.PopularCategoryModel;

import java.util.List;
// انترفيس الكول باك عشان نستدعي الببيلر والبست ديلز الي بالواجهة في حال اشتغلت او حصلت مشكله
public interface IPopularCallbackListener {
    void onPopularLoadSuccess(List<PopularCategoryModel> popularCategoryModels);
    void onPopularLoadFailed(String message);

}
