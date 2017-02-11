'use strict';

angular.module('stepApp')
    .factory('PrlAllowDeductInfo', function ($resource, DateUtils) {
        return $resource('api/prlAllowDeductInfos/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateFromServer(data.updateDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            }
        });
    })
    .factory('PrlAllowDeductInfoUniqueness', function ($resource)
    {
        return $resource('api/prlAllowDeductInfosByTypeAndName/:type/:name', {},
            {
                'get': { method: 'GET', isArray: false}
            });
    })
    .factory('PrlAllowDeductInfoByType', function ($resource)
    {
        return $resource('api/prlAllowDeductInfosByType/:type', {},
            {
                'get': { method: 'GET', isArray: true}
            });
    })
    .factory('PrlAllowDeductInfoByCode', function ($resource)
    {
        return $resource('api/prlAllowDeductInfoByCode/:code', {},
            {
                'get': { method: 'GET', isArray: true}
            });
    })
    .factory('PrlAllowDeductInfoByEconCodes', function ($resource)
    {
        return $resource('api/prlAllowDeductInfoByEconCodes/:codes', {},
            {
                'get': { method: 'GET', isArray: true}
            });
    })
    .factory('PrlAllowDeductInfoByCategoryCheck', function ($resource)
    {
        return $resource('api/prlAllowDeductInfosByCategory/:cat/:alid', {},
            {
                'get': { method: 'GET', isArray: false}
            });
    });
