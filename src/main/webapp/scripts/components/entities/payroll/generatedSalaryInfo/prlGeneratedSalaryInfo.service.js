'use strict';

angular.module('stepApp')
    .factory('PrlGeneratedSalaryInfo', function ($resource, DateUtils) {
        return $resource('api/prlGeneratedSalaryInfos/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.processDate = DateUtils.convertLocaleDateFromServer(data.processDate);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateFromServer(data.updateDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.processDate = DateUtils.convertLocaleDateToServer(data.processDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.processDate = DateUtils.convertLocaleDateToServer(data.processDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            }
        });
    })
    .factory('PrlSalaryGenerationProc', function ($resource)
    {
        return $resource('api/prlGenerateSalaryByStoredProc/', {},
            {
                'update': { method: 'POST'}
            });
    })
    .factory('PrlSalaryDisburseProc', function ($resource,DateUtils)
    {
        return $resource('api/prlDisburseSalaryByStoredProc/', {},
            {
                'update': {
                    method: 'POST',
                    transformRequest: function (data) {
                        data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                        data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                        return angular.toJson(data);
                    }
                }
            });
    })
    .factory('PrlGeneratedSalaryInfoFilter', function ($resource)
    {
        return $resource('api/prlGeneratedSalaryInfosFilter/:year/:month/:saltype', {},
            {
                'get': { method: 'GET', isArray: true}
            });
    })
    .factory('PrlGeneratedSalaryInfoHistory', function ($resource)
    {
        return $resource('api/prlGeneratedSalaryInfosHistory/', {},
            {
                'get': { method: 'GET', isArray: false}
            });
    });
