# YZxing
一款仿微信扫一扫界面，基于zxing实现的扫码库。  
### **实现效果** ###  
![image](https://github.com/MRYangY/YZxing/blob/master/app/src/main/res/drawable-xhdpi/Screenshot_2017-11-03-14-38-37-022_com.example.yz_meitu_2.png)![image](https://github.com/MRYangY/YZxing/blob/master/app/src/main/res/drawable-xhdpi/Screenshot_2017-11-03-13-51-26-886_com.example.yz_meitu_1.png)  
### **使用方式** ###
首先将YZxing-lib库添加到项目中。然后在点击跳转到扫码界面的点击事件中，调用如下方法：  
 Intent intent = new Intent(this, ScannerActivity.class);  
        //这里可以用intent传递一些参数，比如扫码聚焦框尺寸大小，支持的扫码类型。  
//        //设置扫码框的宽  
//        intent.putExtra(Constant.EXTRA_SCANNER_FRAME_WIDTH, 400);  
//        //设置扫码框的高  
//        intent.putExtra(Constant.EXTRA_SCANNER_FRAME_HEIGHT, 400);  
//        //设置扫码框距顶部的位置  
//        intent.putExtra(Constant.EXTRA_SCANNER_FRAME_TOP_PADDING, 100);  
//        Bundle bundle = new Bundle();  
//        //设置支持的扫码类型  
//        bundle.putSerializable(Constant.EXTRA_SCAN_CODE_TYPE, mHashMap);  
//        intent.putExtras(bundle);  
        startActivityForResult(intent, RESULT_REQUEST_CODE);  

这里可以使用intent传递一些配置参数。支持有设置扫码框的大小，及位置；设置支持的扫码类型。目前支持的自定义配置不多，后续有机会再扩充。 跳转的时候要有startActivityForResult来跳转，这样在扫码成功之后，返回的结果可以在onActivityResult方法中处理代码如下：  
@Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
        if (resultCode == RESULT_OK) {  
        
            switch (requestCode) {  
            
                case RESULT_REQUEST_CODE:  
                
                    if (data == null) return;  
                    
                    String type = data.getStringExtra(Constant.EXTRA_RESULT_CODE_TYPE);  
                    
                    String content = data.getStringExtra(Constant.EXTRA_RESULT_CONTENT);  
                    
                    Toast.makeText(MainActivity.this,"codeType:" + type  
                    
                            + "-----content:" + content,Toast.LENGTH_SHORT).show();  
                            
                    break;  
                    
                default:  
                
                    break;  
                    

            }  
            
        }  
        
        super.onActivityResult(requestCode, resultCode, data);  
        
    }

