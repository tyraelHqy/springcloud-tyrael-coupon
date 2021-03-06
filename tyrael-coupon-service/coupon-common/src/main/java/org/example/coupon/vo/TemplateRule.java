package org.example.coupon.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.example.coupon.constant.PeriodType;

/**
 * 优惠券规则对象定义
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemplateRule {

    /**优惠券过期规则 */
    private Expiratioin expiratioin;

    /**优惠券折扣规则 */
    private Discount discount;

    /**优惠券每个人最多可以领几张的限制 */
    private Integer limitation;

    /**使用范围的限制：地域 + 商品类型 */
    private Usage usage;

    /**权重(可以和哪些优惠券叠加使用，需要注意的是同一类的优惠券一定不能叠加：list[],优惠券的唯一编码) */
    private String weight;

    /**
     * 有效期限规则
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Expiratioin{

        /** 有效期规则，对应 PeriodType 的 code 字段 */
        private Integer period;

        /** 有效间隔，只对变动性有效期有效 */
        private Integer gap;

        /** 优惠券模板的失效日期，两类规则都有效 */
        private Long deadline;

        boolean validate(){
            // 最简化校验
            return null != PeriodType.of(period) && gap >0 && deadline >0;
        }
    }

    /**
     * 折扣，需要与类型配合决定
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Discount{

        /** 额度：满减（20），折扣（85），立减（10）*/
        private Integer quota;

        /** 基准，需要满多少才可用 */
        private Integer base;

        boolean validate(){
            return quota>0 && base >0;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Usage{

        /** 省份 */
        private String province;

        /** 城市 */
        private String city;

        /** 商品类型, list[文娱，生鲜，家具，全品类] */
        private String goodsType;

        boolean validate(){
            return StringUtils.isNotEmpty(province) && StringUtils.isNotEmpty(city) && StringUtils.isNotEmpty(goodsType);
        }
    }

    /**
     * 校验功能
     */
    public boolean validate(){
        return expiratioin.validate()
                && discount.validate()
                && limitation > 0
                && usage.validate()
                && StringUtils.isNotEmpty(weight);
    }
}
