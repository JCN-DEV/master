'use strict';

angular.module('stepApp')
    .factory('UmracRoleSetup', function ($resource, DateUtils) {
        return $resource('api/umracRoleSetups/:id', {}, {
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
    .factory('UserRoleRightTree', function ($resource, DateUtils) {
        return $resource('api/umracRoleSetups/rolesRights/', {}, {
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

    .factory('UserRoleRightTreeById', function ($resource, DateUtils) {
        return $resource('api/umracRoleSetups/rolesRightsByRoleId/:id', {}, {
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
    });
