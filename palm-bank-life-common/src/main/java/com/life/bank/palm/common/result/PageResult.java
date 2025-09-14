package com.life.bank.palm.common.result;

import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Collections;
import java.util.List;

/**
 * author:薯条哥搞offer
 */
public class PageResult<T> {
    private Integer totalSize;
    private Integer pageSize;
    private Integer pageNumber;
    private List<T> result;

    public static <T> PageResult<T> mockEmptyPage(Integer pageSize, Integer pageNumber) {
        return new PageResult(NumberUtils.INTEGER_ZERO, pageSize, pageNumber, Collections.emptyList());
    }

    public static <T> PageResult<T> buildOutOfPage(Integer totalSize, Integer pageSize, Integer pageNumber) {
        return new PageResult(totalSize, pageSize, pageNumber, Collections.emptyList());
    }

    public static <T, K> PageResult<T> buildWithPageInfo(PageInfo<K> pageInfo, List<T> results) {
        return new PageResult((int)pageInfo.getTotal(), pageInfo.getPageSize(), pageInfo.getPageNum(), results);
    }

    public static <T, K> PageResult<T> buildByResult(Integer total, Integer pageSize, Integer pageNumber, List<T> results) {
        return new PageResult(total, pageSize, pageNumber, results);
    }

    public Integer getTotalSize() {
        return this.totalSize;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public Integer getPageNumber() {
        return this.pageNumber;
    }

    public List<T> getResult() {
        return this.result;
    }

    public void setTotalSize(Integer totalSize) {
        this.totalSize = totalSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof PageResult)) {
            return false;
        } else {
            PageResult<?> other = (PageResult)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label59: {
                    Object this$totalSize = this.getTotalSize();
                    Object other$totalSize = other.getTotalSize();
                    if (this$totalSize == null) {
                        if (other$totalSize == null) {
                            break label59;
                        }
                    } else if (this$totalSize.equals(other$totalSize)) {
                        break label59;
                    }

                    return false;
                }

                Object this$pageSize = this.getPageSize();
                Object other$pageSize = other.getPageSize();
                if (this$pageSize == null) {
                    if (other$pageSize != null) {
                        return false;
                    }
                } else if (!this$pageSize.equals(other$pageSize)) {
                    return false;
                }

                Object this$pageNumber = this.getPageNumber();
                Object other$pageNumber = other.getPageNumber();
                if (this$pageNumber == null) {
                    if (other$pageNumber != null) {
                        return false;
                    }
                } else if (!this$pageNumber.equals(other$pageNumber)) {
                    return false;
                }

                Object this$result = this.getResult();
                Object other$result = other.getResult();
                if (this$result == null) {
                    if (other$result != null) {
                        return false;
                    }
                } else if (!this$result.equals(other$result)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof PageResult;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $totalSize = this.getTotalSize();
        result = result * 59 + ($totalSize == null ? 43 : $totalSize.hashCode());
        Object $pageSize = this.getPageSize();
        result = result * 59 + ($pageSize == null ? 43 : $pageSize.hashCode());
        Object $pageNumber = this.getPageNumber();
        result = result * 59 + ($pageNumber == null ? 43 : $pageNumber.hashCode());
        Object $result = this.getResult();
        result = result * 59 + ($result == null ? 43 : $result.hashCode());
        return result;
    }

    public String toString() {
        return "PageResult(totalSize=" + this.getTotalSize() + ", pageSize=" + this.getPageSize() + ", pageNumber=" + this.getPageNumber() + ", result=" + this.getResult() + ")";
    }

    public PageResult(Integer totalSize, Integer pageSize, Integer pageNumber, List<T> result) {
        this.totalSize = totalSize;
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
        this.result = result;
    }

    public PageResult() {
    }
}
