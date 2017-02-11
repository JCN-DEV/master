'use strict';

angular.module('stepApp')
    .factory('NameCnclApplicationSearch', function ($resource) {
        return $resource('api/_search/nameCnclApplications/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('NameCnclApplicationList', function ($resource) {
        return $resource('api/nameCnclApplications/nameCnclList/:status', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('NameCnclApplicationListCurrInst', function ($resource) {
        return $resource('api/nameCnclApplications/institute/current', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('NameCnclApplicationByEmpCode', function ($resource) {
        return $resource('api/nameCnclApplications/instEmployee/:code', {}, {
            'get': { method: 'GET', isArray: false}
        });
    });
