'use strict';

angular.module('stepApp')
    .factory('UmracRoleAssignUser', function ($resource, DateUtils) {
        return $resource('api/umracRoleAssignUsers/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    data.updatedTime = DateUtils.convertLocaleDateFromServer(data.updatedTime);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updatedTime = DateUtils.convertLocaleDateToServer(data.updatedTime);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updatedTime = DateUtils.convertLocaleDateToServer(data.updatedTime);
                    return angular.toJson(data);
                }
            }
        });
    })
    .factory('UserExistsChecker', function ($resource, DateUtils) {
        return $resource('api/umracRoleAssignUsers/userId/:id', {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': {method: 'PUT'}
        });
    })
;
