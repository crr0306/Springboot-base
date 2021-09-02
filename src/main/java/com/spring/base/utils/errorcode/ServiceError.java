package com.spring.base.utils.errorcode;

/**
 * 错误码
 * 421000-421100,通用错误码
 * 421101开始,其他错误码
 */
public interface ServiceError {

    /*******************************数据库*************************/
    int UPDATE_ERROR=411000;
    int UPDATE_ERROR_CODE=411000;
    String UPDATE_ERROR_MSG="数据库：更新数据失败";

    int INSERT_ERROR=411001;
    int INSERT_ERROR_CODE=411001;
    String INSERT_ERROR_MSG="数据库：插入数据失败";

    int SELECT_ERROR=411002;
    int SELECT_ERROR_CODE=411002;
    String SELECT_ERROR_MSG="数据库：查询数据失败";
    /**************************************系统问题*****************************************************/

    int SYSTEM_ERROR=421000;
    int SYSTEM_ERROR_CODE=421000;
    String SYSTEM_ERROR_MSG="服务器跑了";


    int MISS_TOKEN=421001;
    int MISS_TOKEN_CODE=421001;
    String MISS_TOKEN_MSG="缺少token";

    int MISS_PARAM=421002;
    int MISS_PARAM_CODE=421002;
    String MISS_PARAM_MSG="缺少参数";

    int SESSION_EXPIRE=421003;
    int SESSION_EXPIRE_CODE=421003;
    String SESSION_EXPIRE_MSG="会话过期";

    int ORDERLINE_LACK=421004;
    int ORDERLINE_LACK_CODE=421004;
    String ORDERLINE_LACK_MSG="缺少排序值";

    int NAME_IS_EMPTY=421005;
    int NAME_IS_EMPTY_CODE=421005;
    String NAME_IS_EMPTY_MSG="名字不能为空";

    int IMG_IS_EMPTY=421006;
    int IMG_IS_EMPTY_CODE=421006;
    String IMG_IS_EMPTY_MSG="图片地址不能为空";

    int TIME_FORMAT_IS_ERROR=421007;
    int TIME_FORMAT_IS_ERROR_CODE=421007;
    String TIME_FORMAT_IS_ERROR_MSG="时间格式是错误的";

    int DOWN_REMOTE_IMAGE_FAILURE=421008;
    int DOWN_REMOTE_IMAGE_FAILURE_CODE=421008;
    String DOWN_REMOTE_IMAGE_FAILURE_MSG="下载远程图片失败";


    /** =================================通用错误start================================= **/
    int CONNECT_MINI_PROGRAM_FAILURE=430006;
    int CONNECT_MINI_PROGRAM_FAILURE_CODE=430006;
    String CONNECT_MINI_PROGRAM_FAILURE_MSG="连接微信服务器失败";

    int GET_AUTH_PHONE_FAILURE=430007;
    int GET_AUTH_PHONE_FAILURE_CODE=430007;
    String GET_AUTH_PHONE_FAILURE_MSG="获取授权手机号失败";


    int POSTTYPE_NAME_IS_EMPTY=430008;
    int POSTTYPE_NAME_IS_EMPTY_CODE=430008;
    String POSTTYPE_NAME_IS_EMPTY_MSG="海报类型名字不能为空";


    int UPLOAD_FILE_IS_EMPTY=430009;
    int UPLOAD_FILE_IS_EMPTY_CODE=430009;
    String UPLOAD_FILE_IS_EMPTY_MSG="文件为空";


    int SAVE_FILE_FAILURE=430010;
    int SAVE_FILE_FAILURE_CODE=430010;
    String SAVE_FILE_FAILURE_MSG="保存文件失败";

    int BUSINESS_CODE_NOT_EXIST=430011;
    int BUSINESS_CODE_NOT_EXIST_CODE=430011;
    String BUSINESS_CODE_NOT_EXIST_MSG="文件业务类型编号在系统不存在";


    int UPLOAD_FILE_EXTNAME_NO_ALLOCATION=430012;
    int UPLOAD_FILE_EXTNAME_NO_ALLOCATION_CODE=430012;
    String UPLOAD_FILE_EXTNAME_NO_ALLOCATION_MSG="文件业务类型编号对应的扩展名未配置";

    int UPLOAD_FILE_SIZE_NO_ALLOCATION=430013;
    int UPLOAD_FILE_SIZE_NO_ALLOCATION_CODE=430013;
    String UPLOAD_FILE_SIZE_NO_ALLOCATION_MSG="文件业务类型编号对应的文件大小未配置";

    int POSTER_LACK_NAME=430014;
    int POSTER_LACK_NAME_CODE=430014;
    String POSTER_LACK_NAME_MSG="海报：缺少名字";

    int POSTER_LACK_TYPE=430015;
    int POSTER_LACK_TYPE_CODE=430015;
    String POSTER_LACK_TYPE_MSG="海报：缺少类型";


    int POSTER_LACK_IMG=430016;
    int POSTER_LACK_IMG_CODE=430016;
    String POSTER_LACK_IMG_MSG="缺少图片链接";

    int ADMIN_IS_NOT_EXIST=430017;
    int ADMIN_IS_NOT_EXIST_CODE=430017;
    String ADMIN_IS_NOT_EXIST_MSG="管理员：不存在";


    int ADMIN_PWD_IS_WRONG=430018;
    int ADMIN_PWD_IS_WRONG_CODE=430018;
    String ADMIN_PWD_IS_WRONG_MSG="管理员：登录密码错误";

    int DEPT_QUERY_FAILURE=430019;
    int DEPT_QUERY_FAILURE_CODE=430019;
    String DEPT_QUERY_FAILURE_MSG="部门：查询部门失败";
    /**************************************banner*****************************************************/

    int BANNER_LACK_NAME=430020;
    int BANNER_LACK_NAME_CODE=430020;
    String BANNER_LACK_NAME_MSG="banner：缺少名字";

    int BANNER_LACK_ORDER=430021;
    int BANNER_LACK_ORDER_CODE=430021;
    String BANNER_LACK_ORDER_MSG="banner：缺少排序值";

    int BANNER_LACK_SKIP=430022;
    int BANNER_LACK_SKIP_CODE=430022;
    String BANNER_LACK_SKIP_MSG="banner：缺少跳转配置";

    int SERVICE_NUMBER_REPEAT=430023;
    int SERVICE_NUMBER_REPEAT_CODE=430023;
    String SERVICE_NUMBER_REPEAT_MSG="回复数重复";

    int SERVICE_NUMBER_LACK=430024;
    int SERVICE_NUMBER_LACK_CODE=430024;
    String SERVICE_NUMBER_LACK_MSG="回复数必填";

    int DICT_NO_LACK=430026;
    int DICT_NO_LACK_CODE=430026;
    String DICT_NO_LACK_MSG="字典编号不存在";
    /**************************************产品*****************************************************/
    int PRODUCT_TYPE_NAME_REPEAT=440000;
    int PRODUCT_TYPE_NAME_REPEAT_CODE=440000;
    String PRODUCT_TYPE_NAME_REPEAT_MSG="名字重复，请重新命名";


    int PRODUCT_TYPE_HAVE_CHILDREN=440001;
    int PRODUCT_TYPE_HAVE_CHILDREN_CODE=440001;
    String PRODUCT_TYPE_HAVE_CHILDREN_MSG="该类目下绑定产品，不能删除";

    /**************************************产品*****************************************************/
    int CATALOG_DELETE_FAILURE=450000;
    int CATALOG_DELETE_FAILURE_CODE=450000;
    String CATALOG_DELETE_FAILURE_MSG="该栏目节点下面存在子节点，请先删除子节点！";
    /**************************************文章*****************************************************/
    int ARTICLE_IS_PUBLISH=460000;
    int ARTICLE_IS_PUBLISH_CODE=460000;
    String ARTICLE_IS_PUBLISH_MSG="文章已发布，无法编辑";













}
