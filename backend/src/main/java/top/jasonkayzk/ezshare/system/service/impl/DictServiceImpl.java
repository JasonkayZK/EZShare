package top.jasonkayzk.ezshare.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.jasonkayzk.ezshare.common.SortUtil;
import top.jasonkayzk.ezshare.common.entity.QueryRequest;
import top.jasonkayzk.ezshare.system.dao.mapper.DictMapper;
import top.jasonkayzk.ezshare.system.entity.Dict;
import top.jasonkayzk.ezshare.system.service.IDictService;

import java.util.Arrays;

/**
 * @author Jasonkay
 */
@Slf4j
@Service("dictService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements IDictService {

    @Override
    public IPage<Dict> findDicts(QueryRequest request, Dict dict) {
        try {
            LambdaQueryWrapper<Dict> queryWrapper = new LambdaQueryWrapper<>();

            if (StringUtils.isNotBlank(dict.getDictKey())) {
                queryWrapper.eq(Dict::getDictKey, dict.getDictKey());
            }
            if (StringUtils.isNotBlank(dict.getDictValue())) {
                queryWrapper.eq(Dict::getDictValue, dict.getDictValue());
            }
            if (StringUtils.isNotBlank(dict.getTableName())) {
                queryWrapper.eq(Dict::getTableName, dict.getTableName());
            }
            if (StringUtils.isNotBlank(dict.getFieldName())) {
                queryWrapper.eq(Dict::getFieldName, dict.getFieldName());
            }

            Page<Dict> page = new Page<>();
            SortUtil.handlePageSort(request, page, true);

            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public Dict findDictById(Long id) {
        return this.baseMapper.selectById(id);
    }

    @Override
    @Transactional
    public void createDict(Dict dict) {
        this.baseMapper.insert(dict);
    }

    @Override
    @Transactional
    public void updateDict(Dict dict) {
        this.baseMapper.updateById(dict);
    }

    @Override
    @Transactional
    @CacheEvict(value = "dicts", allEntries = true)
    public void deleteDicts(String[] dictIds) {
        this.baseMapper.deleteBatchIds(Arrays.asList(dictIds));
    }

}
