package com.carry.recruitment.mapper;

import com.carry.recruitment.entity.Favorite;
import com.carry.recruitment.entity.Job;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface FavoriteMapper {
    public int addFav(Favorite favorite);
    public ArrayList<Favorite> getAll(String stu_id);
    public int delFav(Favorite favorite);
}
