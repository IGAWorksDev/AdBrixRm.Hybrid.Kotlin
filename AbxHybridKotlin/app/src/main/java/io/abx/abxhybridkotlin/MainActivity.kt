package io.abx.abxhybridkotlin

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.webkit.JavascriptInterface
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.igaworks.v2.abxExtensionApi.AbxCommerce
import com.igaworks.v2.abxExtensionApi.AbxCommon
import com.igaworks.v2.core.AdBrixRm
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private var WEB_URL = "http://tech.ad-brix.com/adbrix_hybrid_sample_web/abxrm_hybrid_sample_kotlin.html"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AdBrixRm.setEventUploadCountInterval(AdBrixRm.AdBrixEventUploadCountInterval.MIN)
        AdBrixRm.setEventUploadTimeInterval(AdBrixRm.AdBrixEventUploadTimeInterval.MIN)

        webView.settings.javaScriptEnabled = true

        webView.addJavascriptInterface(AbxJavascriptInterfaceBridge(this), "adbrixrm")

        webView.loadUrl(WEB_URL)


    }

    override fun onResume() {
        super.onResume()

        Toast.makeText(this, "Page Reloaded!!", Toast.LENGTH_SHORT).show()
        webView.reload()
    }

    class AbxJavascriptInterfaceBridge(private var mContext:Context) {

        @JavascriptInterface
        fun purchase(order_id:String, product_id : String, product_name:String, price:Double, quantity:Int, category:String, currency: String, discount:Double, deliveryCharge:Double, paymentMethod: Int ){
            Toast.makeText(mContext, "Sample Purchase Called From Webview", Toast.LENGTH_SHORT).show()

            var sampleCategoryModel = AdBrixRm.CommerceCategoriesModel()

            // 예제에서는 웹에서 전달하는 카테고리 정보에 대해서 / 를 구분자로 하는 계층구조를 가진다.
            // 전달 받은 카테고리 문자열을 구분자를 이용하여 분리하고 AdBrixRm.CommerceCategoriesModel 에 담는다.
            var categoryArr = category.split("/")
            for (item in categoryArr) {
                sampleCategoryModel.setCategory(item)
            }

            // 웹에서 전달 받은 상품 상세 정보를 이용하여 AdBrixRm.CommerceProductModel 에 담는다.
            var sampleProductModel = AdBrixRm.CommerceProductModel()
            sampleProductModel.setProductID(product_id)
                .setProductName(product_name)
                .setPrice(price)
                .setQuantity(quantity)
                .setCategory(sampleCategoryModel)


            // 여러 개의 상품을 한번에 담는 경우를 지원하기 위해서 ArrayList 를 이용하여야 한다.
            var productArrayList = ArrayList<AdBrixRm.CommerceProductModel>()

            // 완성된 ProductModel을 ArrayList에 담는다.
            productArrayList.add(sampleProductModel)

            // 완성된 productArrayList와 나머지 정보를 이용하여 AbxCommerce.purcahse api를 호출한다.
            AbxCommon.purchase(order_id, productArrayList, discount ,deliveryCharge, AdBrixRm.CommercePaymentMethod.getMethodByMethodCode(paymentMethod))

            Log.d("ABXRM_KOTILN_HYBRID",
                "ABXRM PURCHASE EVENT :: "+
                        "\n- sample_order_id : " + order_id +
                        "\n- sample_product_id : " + product_id +
                        "\n- sample_product_name : " + product_name +
                        "\n- sample_price : " + price +
                        "\n- sample_qauntity : " + quantity +
                        "\n- sample_category : " + category +
                        "\n- sample_discount : " + discount +
                        "\n- sample_deliverty_charget : " + deliveryCharge +
                        "\n- sample_payment_method : " + AdBrixRm.CommercePaymentMethod.getMethodByMethodCode(paymentMethod)
            )
        }


        @JavascriptInterface
        fun product_view(product_id : String, product_name:String, price:Double, quantity:Int, category:String) {

            Toast.makeText(mContext, "Sample ProductView Called From Webview", Toast.LENGTH_SHORT).show()

            var sampleCategoryModel = AdBrixRm.CommerceCategoriesModel()

            // 예제에서는 웹에서 전달하는 카테고리 정보에 대해서 / 를 구분자로 하는 계층구조를 가진다.
            // 전달 받은 카테고리 문자열을 구분자를 이용하여 분리하고 AdBrixRm.CommerceCategoriesModel 에 담는다.
            var categoryArr = category.split("/")
            for (item in categoryArr) {
                sampleCategoryModel.setCategory(item)
            }


            // 웹에서 전달 받은 상품 상세 정보를 이용하여 AdBrixRm.CommerceProductModel 에 담는다.
            var sampleProductModel = AdBrixRm.CommerceProductModel()
            sampleProductModel.setProductID(product_id)
                .setProductName(product_name)
                .setPrice(price)
                .setQuantity(quantity)
                .setCategory(sampleCategoryModel)

            // 완성된 ProductModel을 이용하여 AbxCommerce.productView api 를 호출한다.
            AbxCommerce.productView(sampleProductModel)

            Log.d("ABXRM_KOTILN_HYBRID",
                "ABXRM PRODUCT_VIEW EVENT :: "+
                        "\n- sample_product_id : " + product_id +
                        "\n- sample_product_name : " + product_name +
                        "\n- sample_price : " + price +
                        "\n- sample_qauntity : " + quantity +
                        "\n- sample_category : " + category
            )
        }

        @JavascriptInterface
        fun add_to_cart(product_id : String, product_name:String, price:Double, quantity:Int, category:String) {
            Toast.makeText(mContext, "Sample AddToCart Called From Webview", Toast.LENGTH_SHORT).show()

            var sampleCategoryModel = AdBrixRm.CommerceCategoriesModel()

            // 예제에서는 웹에서 전달하는 카테고리 정보에 대해서 / 를 구분자로 하는 계층구조를 가진다.
            // 전달 받은 카테고리 문자열을 구분자를 이용하여 분리하고 AdBrixRm.CommerceCategoriesModel 에 담는다.
            var categoryArr = category.split("/")
            for (item in categoryArr) {
                sampleCategoryModel.setCategory(item)
            }


            // 웹에서 전달 받은 상품 상세 정보를 이용하여 AdBrixRm.CommerceProductModel 에 담는다.
            var sampleProductModel = AdBrixRm.CommerceProductModel()
            sampleProductModel.setProductID(product_id)
                .setProductName(product_name)
                .setPrice(price)
                .setQuantity(quantity)
                .setCategory(sampleCategoryModel)

            // 여러 개의 상품을 한번에 담는 경우를 지원하기 위해서 ArrayList 를 이용하여야 한다.
            var productArrayList = ArrayList<AdBrixRm.CommerceProductModel>()

            // 완성된 ProductModel을 ArrayList에 담는다.
            productArrayList.add(sampleProductModel)

            // 완성된 ArrayList를 이용하여 AbxCommerce.addToCart api를 호출한다.
            AbxCommerce.addToCart(productArrayList)

            Log.d("ABXRM_KOTILN_HYBRID",
                "ABXRM ADD_TO_CART EVENT :: "+
                        "\n- sample_product_id : " + product_id +
                        "\n- sample_product_name : " + product_name +
                        "\n- sample_price : " + price +
                        "\n- sample_quantity : " + quantity +
                        "\n- sample_category : " + category
            )
        }
    }

}
