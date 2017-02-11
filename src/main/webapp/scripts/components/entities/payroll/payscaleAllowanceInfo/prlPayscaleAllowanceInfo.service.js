'use strict';

angular.module('stepApp')
    .factory('PrlPayscaleAllowanceInfo', function ($resource, DateUtils) {
        return $resource('api/prlPayscaleAllowanceInfos/:id', {}, {
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
    }).factory('PrlPayscaleAllowanceInfoByPsGrd', function ($resource)
    {
        return $resource('api/prlPayscaleAllowanceInfosByPsGrd/:psid/:grdid', {},
            {
                'get': { method: 'GET', isArray: true}
            });
    });
