'use strict';

angular.module('stepApp')
    .factory('DlContSCatSet', function ($resource, DateUtils) {
        return $resource('api/dlContSCatSets/:id', {}, {
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
    }) .factory('DlContSCatSetByName', function ($resource) {
        return $resource('api/dlContSCatSets/dlContSCatSetNameUnique/:name', {}, {
            'query': {method: 'GET', isArray: true}
        });
    })
    .factory('DlContSCatSetByCode', function ($resource) {
        return $resource('api/dlContSCatSets/dlContSCatSetCodeUnique/:code', {}, {
            'query': {method: 'GET', isArray: true}
        });
    })


    .factory('FindActiveSubcategory', function ($resource) {
        return $resource('api/dlContSCatSets/allActiveSubCategory', {}, {
            'query': {method: 'GET', isArray: true}
        });
    });

