'use strict';

angular.module('stepApp')
    .factory('MpoTrade', function ($resource, DateUtils) {
        return $resource('api/mpoTrades/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    /*data.cratedDate = DateUtils.convertLocaleDateFromServer(data.cratedDate);
                    data.updateDate = DateUtils.convertLocaleDateFromServer(data.updateDate);*/
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                   /* data.cratedDate = DateUtils.convertLocaleDateToServer(data.cratedDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);*/
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    /*data.cratedDate = DateUtils.convertLocaleDateToServer(data.cratedDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);*/
                    return angular.toJson(data);
                }
            }
        });
    });
