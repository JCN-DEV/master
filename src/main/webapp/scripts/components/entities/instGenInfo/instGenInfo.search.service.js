'use strict';

angular.module('stepApp')
    .factory('InstGenInfoSearch', function ($resource) {
        return $resource('api/_search/instGenInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    })

    .factory('InstGenInfoByStatus', function ($resource,DateUtils) {
        return $resource('api/instGenInfosByStatus/:status', {}, {
            'query': { method: 'GET', isArray: true}
        });
    })
    .factory('InstituteAllInfo', function ($resource,DateUtils) {
        return $resource('api/instGenInfo/instituteAllInfo/:id', {id: '@id'}, {
            'get': { method: 'GET', isArray: false}
        });
    })
.factory('InstituteAllInfosTemp', function ($resource,DateUtils) {
        return $resource('api/instGenInfo/instituteAllInfo/temp/:id', {id: '@id'}, {
            'get': { method: 'GET', isArray: false}
        });
    })


    .factory('InstGenInfosLocalitys', function ($resource,DateUtils) {
        return $resource('api/instGenInfosLocalitys', {}, {
            'get': { method: 'GET', isArray: true}
        });
    })
    .factory('InstGenInfosByCurrentUser', function ($resource,DateUtils) {
        return $resource('api/instGenInfosByCurrentUser', {}, {
            'get': { method: 'GET', isArray: true}
        });
    }).factory('InstGenInfosRejectedList', function ($resource,DateUtils) {
        return $resource('api/instGenInfos/rejectedList', {}, {
            'get': { method: 'GET', isArray: true}
        });
    }).factory('InstGenInfosPendingInfoList', function ($resource,DateUtils) {
        return $resource('api/instGenInfos/pendingInfo', {}, {
            'get': { method: 'GET', isArray: true}
        });
    }).factory('InstGenInfosPending', function ($resource,DateUtils) {
        return $resource('api/instGenInfosPending', {}, {
            'get': { method: 'GET', isArray: true}
        });
    });
