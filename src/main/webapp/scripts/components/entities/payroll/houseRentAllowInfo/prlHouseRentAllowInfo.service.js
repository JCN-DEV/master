'use strict';

angular.module('stepApp')
    .factory('PrlHouseRentAllowInfo', function ($resource, DateUtils) {
        return $resource('api/prlHouseRentAllowInfos/:id', {}, {
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
    .factory('PrlHouseRentAllowInfoMinMaxByLocality', function ($resource)
    {
        return $resource('api/prlHouseRentAllowInfosMinMaxByLocality/:gztid/:lclstid', {},
            {
                'get': { method: 'GET', isArray: false}
            });
    });
