'use strict';

angular.module('stepApp')
    .factory('DlContCatSet', function ($resource, DateUtils) {
        return $resource('api/dlContCatSets/:id', {}, {
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
    }) .factory('DlContCatSetByName', function ($resource) {
                        return $resource('api/dlContCatSets/dlContCatSetUnique/:name', {}, {
                            'query': {method: 'GET', isArray: true}
                        });
                    })
    .factory('DlContCatSetByCode', function ($resource) {
                        return $resource('api/dlContCatSets/dlContCatSetCodeUnique/:code', {}, {
                            'query': {method: 'GET', isArray: true}
                        });
                    })
    .factory('FindActivcategory', function ($resource) {
        return $resource('api/dlContCatSets/allActiveTypes', {}, {
            'query': {method: 'GET', isArray: true}
        });
    });
