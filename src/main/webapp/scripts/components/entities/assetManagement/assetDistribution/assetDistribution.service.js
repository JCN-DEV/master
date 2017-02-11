
'use strict';

angular.module('stepApp')
    .factory('AssetDistribution', function ($resource, DateUtils) {
        return $resource('api/assetDistributions/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.assignedDdate = DateUtils.convertLocaleDateFromServer(data.assignedDdate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.assignedDdate = DateUtils.convertLocaleDateToServer(data.assignedDdate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.assignedDdate = DateUtils.convertLocaleDateToServer(data.assignedDdate);
                    return angular.toJson(data);
                }
            }
        });
    });
