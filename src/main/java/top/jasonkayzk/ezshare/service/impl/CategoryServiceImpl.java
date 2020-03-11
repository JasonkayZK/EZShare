package top.jasonkayzk.ezshare.service.impl;

import com.zhazhapan.util.Checker;
import top.jasonkayzk.ezshare.dao.CategoryDAO;
import top.jasonkayzk.ezshare.entity.Category;
import top.jasonkayzk.ezshare.modules.constant.DefaultValues;
import top.jasonkayzk.ezshare.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pantao
 * @since 2018/1/30
 */
@Service
public class CategoryServiceImpl implements ICategoryService {

    private final CategoryDAO categoryDAO;

    @Autowired
    public CategoryServiceImpl(CategoryDAO categoryDAO) {this.categoryDAO = categoryDAO;}

    @Override
    public boolean insert(String name) {
        return Checker.isNotNull(name) && categoryDAO.insertCategory(name);
    }

    @Override
    public boolean remove(int id) {
        return isCategorized(id) && categoryDAO.removeCategoryById(id);
    }

    @Override
    public boolean update(int id, String name) {
        return Checker.isNotEmpty(name) && isCategorized(id) && categoryDAO.updateNameById(id, name);
    }

    private boolean isCategorized(int id) {
        return !DefaultValues.UNCATEGORIZED.equals(getById(id).getName());
    }

    @Override
    public Category getById(int id) {
        return categoryDAO.getCategoryById(id);
    }

    @Override
    public List<Category> list() {
        return categoryDAO.listCategory();
    }

    @Override
    public int getIdByName(String name) {
        try {
            return categoryDAO.getIdByName(name);
        } catch (Exception e) {
            return Integer.MAX_VALUE;
        }
    }
}
