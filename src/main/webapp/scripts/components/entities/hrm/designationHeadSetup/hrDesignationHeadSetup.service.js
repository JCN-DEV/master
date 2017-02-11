'use strict';

angular.module('stepApp')
    .factory('HrDesignationHeadSetup', function ($resource, DateUtils) {
        return $resource('api/hrDesignationHeadSetups/:id', {}, {
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
    .factory('HrDesignationHeadSetupByStatus', function ($resource) {
        return $resource('api/hrDesignationHeadSetupsByStat/:stat', {}, {
            'get': { method: 'GET', isArray: true}
        });
    })
    .factory('HrDesignationHeadSetupByType', function ($resource) {
        return $resource('api/hrDesignationHeadSetupsByType/:type', {}, {
            'get': { method: 'GET', isArray: true}
        });
    });
