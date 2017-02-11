'use strict';

angular.module('stepApp')
    .factory('InstLevel', function ($resource, DateUtils) {
        return $resource('api/instLevels/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.createdDate = DateUtils.convertLocaleDateFromServer(data.createdDate);
                    data.updatedDate = DateUtils.convertLocaleDateFromServer(data.updatedDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.createdDate = DateUtils.convertLocaleDateToServer(data.createdDate);
                    data.updatedDate = DateUtils.convertLocaleDateToServer(data.updatedDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.createdDate = DateUtils.convertLocaleDateToServer(data.createdDate);
                    data.updatedDate = DateUtils.convertLocaleDateToServer(data.updatedDate);
                    return angular.toJson(data);
                }
            }
        });
    })
    .factory('InstLevelByCode', function ($resource) {
                    return $resource('api/instLevels/instCodeUnique/:code', {}, {
                        'query': {method: 'GET', isArray: true}
                    });
                })
    .factory('InstLevelByName', function ($resource) {
        return $resource('api/instLevels/instNameUnique/:name', {}, {
            'query': {method: 'GET', isArray: true}
        });
    });
