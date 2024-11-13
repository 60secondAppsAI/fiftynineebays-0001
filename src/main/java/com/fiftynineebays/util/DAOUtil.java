package com.fiftynineebays.util;

import java.util.Collection;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;


/**
 * Utility class to handle DAO related functionalities.
 */
public class DAOUtil {

	public static final String LEFT_JOIN_TYPE = "left";
	public static final String RIGHT_JOIN_TYPE = "right";
	
	// the logger
	private final static Logger logger = LoggerFactory.getLogger(DAOUtil.class);


	/**
	 * Resolves a Path for a given root entity type and an inputCol 
	 * representing the first level and nested attributes of the entity. 
	 * 
	 * @param <T> o the root entity type
	 * @param inputCol the input column representing the attributes.
	 * @param joinType the join type for the filter.
	 * 
	 * @return the Path represented by the input attributes.
	 * 
	 */
	public static <T> Path resolvePath(Root<T> o, String inputCol, Optional<String> joinType)
	{
		Path path = null;
		JoinType tableJoinType = JoinType.INNER;
		
		if (Optional.ofNullable(joinType).isPresent())
		{
			if (joinType.get().equals(LEFT_JOIN_TYPE))
				tableJoinType = JoinType.LEFT;
			else if (joinType.get().equals(RIGHT_JOIN_TYPE))
				tableJoinType = JoinType.RIGHT;
		}

		logger.debug("Input array: {}, Table join type: {}", inputCol, tableJoinType);
		
		String[] splits = inputCol.split("\\."); // i.e. permissionGroup.name

		try
		{
			path = o.<String> get(splits[0]);
			Join join = null;

			for (int i = 1; i < splits.length; i++) {
				
				// Always add to the parent join to handle collections for nested attributes.
				// add join to the root if this is the first time in.;
				if (join == null) {
					join = o.join(splits[i - 1], tableJoinType); // join to the root
				} else {
					join = join.join(splits[i - 1], tableJoinType); // join to the previous join;
				}
				
				// this block checks to see if we're dealing with a Collection object (List, Set, etc..) then we make sure to Join that class so that we can filter on attributes of
				// the List elements.
				if (Collection.class.isAssignableFrom(path.getJavaType())) { // look at previous revision of this method to see another approach, but less generic;
					// now add the attribute that we're on to the path.
					path = join.get(splits[i]);
				} else {
					path = path.get(splits[i]);
				}
			}

			if (path == null) {
				path = o.get(splits[0]);
			}
		}
		catch (Exception e)
		{
			logger.error("Exception occured while resolving path for {}", inputCol, e);
		}

		return path;
	}
}