package com.seetatech.ad.http;

import com.seetatech.ad.data.http.GetGoodsInfoResponse;
import com.seetatech.ad.data.http.ScanResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by xjh on 18-6-25.
 */

public class HttpTest {

    public static List<ScanResponse.ScanResult> makeTestScanResult() {
        List<ScanResponse.ScanResult> scanResultList = new ArrayList<>();

        List<ScanResponse.ScanResult.ScanResultInfo> scanResultInfoList_0 = new ArrayList<>();

        ScanResponse.ScanResult.ScanResultInfo scanResultInfo_a = new ScanResponse.ScanResult.ScanResultInfo();
        scanResultInfo_a.setLabel("kkkl_ldkl_500ml");
        List<Float[]> rectList_a = new ArrayList<>();
        rectList_a.add(new Float[]{200.0f, 300.0f, 260.0f, 370.0f});
        rectList_a.add(new Float[]{200.0f, 300.0f, 260.0f, 370.0f});
        rectList_a.add(new Float[]{300.0f, 400.0f, 500.0f, 600.0f});
        rectList_a.add(new Float[]{240.0f, 250.0f, 360.0f, 370.0f});
        scanResultInfo_a.setRect_list(rectList_a);

        scanResultInfoList_0.add(scanResultInfo_a);

        ScanResponse.ScanResult.ScanResultInfo scanResultInfo_b = new ScanResponse.ScanResult.ScanResultInfo();
        scanResultInfo_b.setLabel("kkkl_xb_500ml");
        List<Float[]> rectList_b = new ArrayList<>();
        rectList_b.add(new Float[]{100.0f, 200.0f, 250.0f, 260.0f});
        rectList_b.add(new Float[]{200.0f, 230.0f, 260.0f, 270.0f});
        rectList_b.add(new Float[]{300.0f, 400.0f, 500.0f, 600.0f});
        scanResultInfo_b.setRect_list(rectList_b);

        scanResultInfoList_0.add(scanResultInfo_b);

        ScanResponse.ScanResult scanResult0 = new ScanResponse.ScanResult();
        scanResult0.setCamera_id(0);
        scanResult0.setInfo(scanResultInfoList_0);
        scanResultList.add(scanResult0);

        List<ScanResponse.ScanResult.ScanResultInfo> scanResultInfoList_1 = new ArrayList<>();

        ScanResponse.ScanResult.ScanResultInfo scanResultInfo_a_1 = new ScanResponse.ScanResult.ScanResultInfo();
        scanResultInfo_a_1.setLabel("kkkl_ldkl_500ml");
        List<Float[]> rectList_a_1 = new ArrayList<>();
        rectList_a_1.add(new Float[]{150.0f, 250.0f, 350.0f, 360.0f});
        rectList_a_1.add(new Float[]{200.0f, 300.0f, 260.0f, 270.0f});
        rectList_a_1.add(new Float[]{240.0f, 250.0f, 360.0f, 370.0f});
        scanResultInfo_a_1.setRect_list(rectList_a_1);
        scanResultInfoList_1.add(scanResultInfo_a_1);

        ScanResponse.ScanResult.ScanResultInfo scanResultInfo_b_1 = new ScanResponse.ScanResult.ScanResultInfo();
        scanResultInfo_b_1.setLabel("kkkl_xb_500ml");
        List<Float[]> rectList_b_1 = new ArrayList<>();
        rectList_b_1.add(new Float[]{100.0f, 100.0f, 260.0f, 270.0f});
        rectList_b_1.add(new Float[]{2.0f, 3.0f, 60.0f, 70.0f});
        scanResultInfo_b_1.setRect_list(rectList_b_1);
        scanResultInfoList_1.add(scanResultInfo_b_1);

        ScanResponse.ScanResult scanResult1 = new ScanResponse.ScanResult();
        scanResult1.setCamera_id(1);
        scanResult1.setInfo(scanResultInfoList_1);
        scanResultList.add(scanResult1);

        List<ScanResponse.ScanResult.ScanResultInfo> scanResultInfoList_2 = new ArrayList<>();

        ScanResponse.ScanResult.ScanResultInfo scanResultInfo_a_2 = new ScanResponse.ScanResult.ScanResultInfo();
        scanResultInfo_a_2.setLabel("kkkl_ldkl_500ml");
        List<Float[]> rectList_a_2 = new ArrayList<>();
        rectList_a_2.add(new Float[]{3.0f, 4.0f, 50.0f, 60.0f});
        rectList_a_2.add(new Float[]{4.0f, 5.0f, 60.0f, 70.0f});
        scanResultInfo_a_2.setRect_list(rectList_a_2);
        scanResultInfoList_2.add(scanResultInfo_a_2);

        ScanResponse.ScanResult.ScanResultInfo scanResultInfo_b_2 = new ScanResponse.ScanResult.ScanResultInfo();
        scanResultInfo_b_2.setLabel("kkkl_xb_500ml");
        List<Float[]> rectList_b_2 = new ArrayList<>();
        rectList_b_2.add(new Float[]{1.0f, 2.0f, 50.0f, 60.0f});
        rectList_b_2.add(new Float[]{2.0f, 3.0f, 60.0f, 70.0f});
        rectList_b_2.add(new Float[]{200.0f, 300.0f, 260.0f, 370.0f});
        rectList_b_2.add(new Float[]{4.0f, 5.0f, 60.0f, 70.0f});
        scanResultInfo_b_2.setRect_list(rectList_b_2);
        scanResultInfoList_2.add(scanResultInfo_b_2);

        ScanResponse.ScanResult scanResult2 = new ScanResponse.ScanResult();
        scanResult2.setCamera_id(2);
        scanResult2.setInfo(scanResultInfoList_2);
        scanResultList.add(scanResult2);

        List<ScanResponse.ScanResult.ScanResultInfo> scanResultInfoList_3 = new ArrayList<>();

        ScanResponse.ScanResult.ScanResultInfo scanResultInfo_a_3 = new ScanResponse.ScanResult.ScanResultInfo();
        scanResultInfo_a_3.setLabel("kkkl_ldkl_500ml");
        List<Float[]> rectList_a_3 = new ArrayList<>();
        rectList_a_3.add(new Float[]{200.0f, 300.0f, 260.0f, 370.0f});
        rectList_a_3.add(new Float[]{2.0f, 3.0f, 60.0f, 70.0f});
        rectList_a_3.add(new Float[]{200.0f, 300.0f, 260.0f, 370.0f});
        rectList_a_3.add(new Float[]{4.0f, 5.0f, 60.0f, 70.0f});
        scanResultInfo_a_3.setRect_list(rectList_a_3);
        scanResultInfoList_3.add(scanResultInfo_a_3);

        ScanResponse.ScanResult.ScanResultInfo scanResultInfo_b_3 = new ScanResponse.ScanResult.ScanResultInfo();
        scanResultInfo_b_3.setLabel("kkkl_xb_500ml");
        List<Float[]> rectList_b_3 = new ArrayList<>();
        rectList_b_3.add(new Float[]{1.0f, 2.0f, 50.0f, 60.0f});
        rectList_b_3.add(new Float[]{2.0f, 3.0f, 60.0f, 70.0f});
        rectList_b_3.add(new Float[]{200.0f, 300.0f, 260.0f, 370.0f});
        rectList_b_3.add(new Float[]{4.0f, 5.0f, 60.0f, 70.0f});
        scanResultInfo_b_3.setRect_list(rectList_b_3);
        scanResultInfoList_3.add(scanResultInfo_b_3);

        ScanResponse.ScanResult scanResult3 = new ScanResponse.ScanResult();
        scanResult3.setCamera_id(3);
        scanResult3.setInfo(scanResultInfoList_3);
        scanResultList.add(scanResult3);

        return scanResultList;
    }

    public static List<ScanResponse.ScanResult> makeTestScanResult2() {
        List<ScanResponse.ScanResult> scanResultList = new ArrayList<>();

        List<ScanResponse.ScanResult.ScanResultInfo> scanResultInfoList_0 = new ArrayList<>();

        ScanResponse.ScanResult.ScanResultInfo scanResultInfo_a = new ScanResponse.ScanResult.ScanResultInfo();
        scanResultInfo_a.setLabel("kkkl_ldkl_500ml");
        List<Float[]> rectList_a = new ArrayList<>();
        rectList_a.add(new Float[]{100.0f, 200.0f, 250.0f, 260.0f});
        rectList_a.add(new Float[]{200.0f, 300.0f, 260.0f, 370.0f});
        rectList_a.add(new Float[]{40.0f, 50.0f, 160.0f, 170.0f});
        scanResultInfo_a.setRect_list(rectList_a);

        scanResultInfoList_0.add(scanResultInfo_a);

        ScanResponse.ScanResult.ScanResultInfo scanResultInfo_b = new ScanResponse.ScanResult.ScanResultInfo();
        scanResultInfo_b.setLabel("kkkl_xb_500ml");
        List<Float[]> rectList_b = new ArrayList<>();
        rectList_b.add(new Float[]{100.0f, 200.0f, 250.0f, 260.0f});
        rectList_b.add(new Float[]{200.0f, 300.0f, 260.0f, 370.0f});
        scanResultInfo_b.setRect_list(rectList_b);

        scanResultInfoList_0.add(scanResultInfo_b);

        ScanResponse.ScanResult scanResult0 = new ScanResponse.ScanResult();
        scanResult0.setCamera_id(0);
        scanResult0.setInfo(scanResultInfoList_0);
        scanResultList.add(scanResult0);

        List<ScanResponse.ScanResult.ScanResultInfo> scanResultInfoList_1 = new ArrayList<>();

        ScanResponse.ScanResult.ScanResultInfo scanResultInfo_a_1 = new ScanResponse.ScanResult.ScanResultInfo();
        scanResultInfo_a_1.setLabel("kkkl_ldkl_500ml");
        List<Float[]> rectList_a_1 = new ArrayList<>();
        rectList_a_1.add(new Float[]{100.0f, 200.0f, 500.0f, 600.0f});
        rectList_a_1.add(new Float[]{200.0f, 300.0f, 600.0f, 700.0f});
        rectList_a_1.add(new Float[]{400.0f, 500.0f, 600.0f, 700.0f});
        scanResultInfo_a_1.setRect_list(rectList_a_1);
        scanResultInfoList_1.add(scanResultInfo_a_1);

        ScanResponse.ScanResult.ScanResultInfo scanResultInfo_b_1 = new ScanResponse.ScanResult.ScanResultInfo();
        scanResultInfo_b_1.setLabel("kkkl_xb_500ml");
        List<Float[]> rectList_b_1 = new ArrayList<>();
        rectList_b_1.add(new Float[]{1.0f, 2.0f, 50.0f, 60.0f});
        rectList_b_1.add(new Float[]{2.0f, 3.0f, 60.0f, 70.0f});
        scanResultInfo_b_1.setRect_list(rectList_b_1);
        scanResultInfoList_1.add(scanResultInfo_b_1);

        ScanResponse.ScanResult scanResult1 = new ScanResponse.ScanResult();
        scanResult1.setCamera_id(1);
        scanResult1.setInfo(scanResultInfoList_1);
        scanResultList.add(scanResult1);

        List<ScanResponse.ScanResult.ScanResultInfo> scanResultInfoList_2 = new ArrayList<>();

        ScanResponse.ScanResult.ScanResultInfo scanResultInfo_a_2 = new ScanResponse.ScanResult.ScanResultInfo();
        scanResultInfo_a_2.setLabel("kkkl_ldkl_500ml");
        List<Float[]> rectList_a_2 = new ArrayList<>();
        rectList_a_2.add(new Float[]{3.0f, 4.0f, 50.0f, 60.0f});
        rectList_a_2.add(new Float[]{4.0f, 5.0f, 60.0f, 70.0f});
        scanResultInfo_a_2.setRect_list(rectList_a_2);
        scanResultInfoList_2.add(scanResultInfo_a_2);

        ScanResponse.ScanResult.ScanResultInfo scanResultInfo_b_2 = new ScanResponse.ScanResult.ScanResultInfo();
        scanResultInfo_b_2.setLabel("kkkl_xb_500ml");
        List<Float[]> rectList_b_2 = new ArrayList<>();
        rectList_b_2.add(new Float[]{1.0f, 2.0f, 50.0f, 60.0f});
        rectList_b_2.add(new Float[]{2.0f, 3.0f, 60.0f, 70.0f});
        rectList_b_2.add(new Float[]{200.0f, 300.0f, 260.0f, 370.0f});
        rectList_b_2.add(new Float[]{4.0f, 5.0f, 60.0f, 70.0f});
        scanResultInfo_b_2.setRect_list(rectList_b_2);
        scanResultInfoList_2.add(scanResultInfo_b_2);

        ScanResponse.ScanResult scanResult2 = new ScanResponse.ScanResult();
        scanResult2.setCamera_id(2);
        scanResult2.setInfo(scanResultInfoList_2);
        scanResultList.add(scanResult2);

        List<ScanResponse.ScanResult.ScanResultInfo> scanResultInfoList_3 = new ArrayList<>();

        ScanResponse.ScanResult.ScanResultInfo scanResultInfo_a_3 = new ScanResponse.ScanResult.ScanResultInfo();
        scanResultInfo_a_3.setLabel("kkkl_ldkl_500ml");
        List<Float[]> rectList_a_3 = new ArrayList<>();
        rectList_a_3.add(new Float[]{1.0f, 2.0f, 50.0f, 60.0f});
        rectList_a_3.add(new Float[]{2.0f, 3.0f, 60.0f, 70.0f});
        rectList_a_3.add(new Float[]{3.0f, 4.0f, 50.0f, 60.0f});
        rectList_a_3.add(new Float[]{200.0f, 300.0f, 260.0f, 370.0f});
        scanResultInfo_a_3.setRect_list(rectList_a_3);
        scanResultInfoList_3.add(scanResultInfo_a_3);

        ScanResponse.ScanResult.ScanResultInfo scanResultInfo_b_3 = new ScanResponse.ScanResult.ScanResultInfo();
        scanResultInfo_b_3.setLabel("kkkl_xb_500ml");
        List<Float[]> rectList_b_3 = new ArrayList<>();
        rectList_b_3.add(new Float[]{1.0f, 2.0f, 50.0f, 60.0f});
        rectList_b_3.add(new Float[]{200.0f, 300.0f, 260.0f, 370.0f});
        rectList_b_3.add(new Float[]{3.0f, 4.0f, 50.0f, 60.0f});
        rectList_b_3.add(new Float[]{4.0f, 5.0f, 60.0f, 70.0f});
        scanResultInfo_b_3.setRect_list(rectList_b_3);
        scanResultInfoList_3.add(scanResultInfo_b_3);

        ScanResponse.ScanResult scanResult3 = new ScanResponse.ScanResult();
        scanResult3.setCamera_id(3);
        scanResult3.setInfo(scanResultInfoList_3);
        scanResultList.add(scanResult3);

        return scanResultList;
    }

    public static List<GetGoodsInfoResponse.Product> getGoodsInfo(Set<String> goodsCodes) {
        List<GetGoodsInfoResponse.Product> productList = new ArrayList<>();

        for (String code : goodsCodes) {
            GetGoodsInfoResponse.Product product = new GetGoodsInfoResponse.Product();
            if ("kkkl_ldkl_500ml".equals(code)) {
                product.setProduct_name("可口可乐");
                product.setImage_url("http://img3.imgtn.bdimg.com/it/u=934762668,507152248&fm=214&gp=0.jpg");
                product.setProduct_price("3.50");
                product.setProduct_code("kkkl_ldkl_500ml");
                productList.add(product);
            } else if ("kkkl_xb_500ml".equals(code)) {
                product.setProduct_name("雪碧");
                product.setImage_url("http://imgqn.koudaitong.com/upload_files/2015/05/17/Flpm9miCkh4XAxelewWiaoSpu_DU.JPG%21580x580.jpg");
                product.setProduct_price("3.50");
                product.setProduct_code("kkkl_xb_500ml");
                productList.add(product);
            } else if ("nfsq_yytrs_550ml".equals(code)) {
                product.setProduct_name("农夫山泉");
                product.setImage_url("http://img003.hc360.cn/hb/MTQ2ODI1Mjc1MzM1NC03NjAxNjExMTQ=.jpg");
                product.setProduct_price("1.50");
                product.setProduct_code("nfsq_yytrs_550ml");
                productList.add(product);
            } else if ("jlb_cmw_560ml".equals(code)) {
                product.setProduct_name("健力宝");
                product.setImage_url("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1534426993660&di=29a44ac939d2312573a65abf71aa01df&imgtype=0&src=http%3A%2F%2Fimgqn.koudaitong.com%2Fupload_files%2F2015%2F06%2F26%2FFlcW1YtzSMRf3luykIAQ44aykjXD.jpg%2521730x0.jpg");
                product.setProduct_price("1.50");
                product.setProduct_code("jlb_cmw_560ml");
                productList.add(product);
            } else if ("yb_yycjs_555ml".equals(code)) {
                product.setProduct_name("怡宝");
                product.setImage_url("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1534427136598&di=b0b10add79832279dfc628ace4c24bdd&imgtype=0&src=http%3A%2F%2Fimg.yzcdn.cn%2Fupload_files%2F2015%2F12%2F14%2FFkveedfH22RAFpSqj4715ZsGMuKI.gif%2521730x0.jpg");
                product.setProduct_price("1.50");
                product.setProduct_code("yb_yycjs_555ml");
                productList.add(product);
            } else if ("bss_yytrkqs_570ml".equals(code)) {
                product.setProduct_name("百岁山");
                product.setImage_url("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1534427179137&di=fbe8256cf1f5ab87a5c8b4bdbddded83&imgtype=0&src=http%3A%2F%2Fm.360buyimg.com%2Fn12%2Fjfs%2Ft5221%2F48%2F954826664%2F147030%2Fa6f26a0a%2F5909afaeNe41cede7.jpg%2521q70.jpg");
                product.setProduct_price("1.50");
                product.setProduct_code("bss_yytrkqs_570ml");
                productList.add(product);
            } else if ("xbxwj_nmw_500ml".equals(code)) {
                product.setProduct_name("纤维雪碧");
                product.setImage_url("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1534427230821&di=565322daf16f29005e86534403fb681d&imgtype=0&src=http%3A%2F%2Fpic.womai.com%2Fupload%2F2018%2F4%2F25%2F20180425034344337.jpg");
                product.setProduct_price("1.50");
                product.setProduct_code("xbxwj_nmw_500ml");
                productList.add(product);
            } else if ("whh_gylz_bbz_360g".equals(code)) {
                product.setProduct_name("哇哈哈八宝粥");
                product.setImage_url("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1534427262833&di=782b6568d3dc0996da6f300b717f6f08&imgtype=0&src=http%3A%2F%2Fsdjshop.com%2Fimages%2F201508%2Fgoods_img%2F176_P_1439602522108.jpg");
                product.setProduct_price("1.50");
                product.setProduct_code("whh_gylz_bbz_360g");
                productList.add(product);
            } else if ("jg_jdblc_310ml".equals(code)) {
                product.setProduct_name("加多宝");
                product.setImage_url("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1534427290566&di=f18d6f5c0d63f7d1aff0b5c248ebcc9c&imgtype=0&src=http%3A%2F%2Fatt2.citysbs.com%2Fhangzhou%2F2015%2F07%2F02%2F15%2Fmiddle_595x744-155610_v2_18501435823770226_98808252a6ebe5a1303337aadff7526b.png");
                product.setProduct_price("1.50");
                product.setProduct_code("jg_jdblc_310ml");
                productList.add(product);
            } else if ("wt_yydn_250ml".equals(code)) {
                product.setProduct_name("原味豆奶(蓝色)");
                product.setImage_url("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1534427347420&di=52eb21e77622e485c54c0771b7e4964f&imgtype=0&src=http%3A%2F%2Fd10.yihaodianimg.com%2FN05%2FM07%2F01%2FD5%2FCgQI01PpXTCAFtRkAAE_2ytkyGU84000_640x640.jpg");
                product.setProduct_price("1.50");
                product.setProduct_code("wt_yydn_250ml");
                productList.add(product);
            } else if ("wt_nmc_250ml".equals(code)) {
                product.setProduct_name("柠檬茶(黄色)");
                product.setImage_url("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1534427417874&di=7349d51d58ebdf42f22899a9b1d13977&imgtype=0&src=http%3A%2F%2Fwww.kzj365.com%2Fimages%2F201507%2Fsource_img%2F19759_P_1438162841606.jpg");
                product.setProduct_price("1.50");
                product.setProduct_code("wt_nmc_250ml");
                productList.add(product);
            } else if ("wt_hdn_250ml".equals(code)) {
                product.setProduct_name("黑豆奶");
                product.setImage_url("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1534427452127&di=e31b05fb1eb913b196bc04d179964d49&imgtype=0&src=http%3A%2F%2Fwww.jxmall.com%2Fpic%2Fproduct%2F2014%2F08%2Fp18v7932qk1hap1nu785g96r14nd2.jpg");
                product.setProduct_price("1.50");
                product.setProduct_code("wt_hdn_250ml");
                productList.add(product);
            }
        }
        return productList;
    }
}
