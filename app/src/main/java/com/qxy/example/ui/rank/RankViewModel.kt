package com.qxy.example.ui.rank

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RankViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "这里是排行榜。请注意，由于目前没有UI界面，又因为目前完成获取榜单数据功能，本版本的App每打开一次，就会" +
                "调用一次榜单接口，一个测试应用全生命周期仅能调用100次，目前请勿频繁打开此应用。获取到的字段可以在Logcat" +
                "搜索rank。筛选Log.e，即error等级"
    }
    val text: LiveData<String> = _text
}