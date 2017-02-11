'use strict';

angular.module('stepApp')
    .factory('JasperReport', function ($resource, DateUtils) {
        return $resource('api/jasperReports/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    //data.createdDate = DateUtils.convertLocaleDateFromServer(data.createdDate);
                    //data.updatedDate = DateUtils.convertLocaleDateFromServer(data.updatedDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    //data.createdDate = DateUtils.convertLocaleDateFromServer(data.createdDate);
                    //data.updatedDate = DateUtils.convertLocaleDateFromServer(data.updatedDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    //data.createdDate = DateUtils.convertLocaleDateFromServer(data.createdDate);
                    //data.updatedDate = DateUtils.convertLocaleDateFromServer(data.updatedDate);
                    return angular.toJson(data);
                }
            }
        });
    })


    .factory('GetJasperParamByJasperReport', function ($resource) {
        return $resource('api/report/:id', {
            'query': { method: 'GET', isArray: true}
        });
    })

    .factory('GetAllJasperReportByModule', function ($resource) {
        return $resource('api/reportByModule/:module',{
            'query': { method: 'GET', isArray: true}
        });
    })
    .factory('GetReportsByModule', function ($resource) {
        return $resource('api/moduleReports/:module',{
            'query': { method: 'GET', isArray: true}
        });
    })
   ;


