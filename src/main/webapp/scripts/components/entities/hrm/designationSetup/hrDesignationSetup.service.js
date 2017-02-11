'use strict';

angular.module('stepApp')
    .factory('HrDesignationSetup', function ($resource, DateUtils) {
        return $resource('api/hrDesignationSetups/:id', {}, {
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
    .factory('HrDesignationSetupByStatus', function ($resource) {
        return $resource('api/hrDesignationSetupsByStat/:stat', {}, {
            'get': { method: 'GET', isArray: true}
        });
    })
    .factory('HrDesignationSetupUniqueness', function ($resource)
    {
        return $resource('api/hrDesignationSetupsByOrgTypeAndFilters/:orgtype/:desigid/:refid', {},
            {
                'get': { method: 'GET', isArray: false}
            });
    })
    .factory('HrDesignationSetupMpoUniqueness', function ($resource)
    {
        return $resource('api/hrDesignationSetupsByTypeLevelCat/:dtype/:lvlid/:catId', {},
            {
                'get': { method: 'GET', isArray: false}
            });
    })
    .factory('HrDesignationSetupByType', function ($resource) {
        return $resource('api/hrDesignationSetupsByType/:type', {}, {
            'get': { method: 'GET', isArray: true}
        });
    });
