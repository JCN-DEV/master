'use strict';

angular.module('stepApp')
    .factory('InstEmplDesignation', function ($resource, DateUtils) {
        return $resource('api/instEmplDesignations/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if(data){
                        data = angular.fromJson(data);
                        return data;
                    }

                }
            },
            'update': { method:'PUT' }
        });
    })
    .factory('AllInstEmplDesignationsList', function ($resource, DateUtils) {
        return $resource('api/allInstEmplDesignationsList', {}, {
            'get': {
                method: 'GET',
                isArray: true,
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            }
        });
    })
  .factory('InstDesignationByCode', function ($resource) {
                    return $resource('api/instEmplDesignations/instCodeUnique/:code', {}, {
                        'query': {method: 'GET', isArray: true}
                    });
                })
    .factory('InstDesignationByName', function ($resource) {
        return $resource('api/instEmplDesignations/instNameUnique/:name', {}, {
            'query': {method: 'GET', isArray: true}
        });
    }).factory('InstDesignationByInstLevel', function ($resource) {
        return $resource('api/instEmplDesignations/instLevel/:id', {}, {
            'query': {method: 'GET', isArray: true}
        });
    });
