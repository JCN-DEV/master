'use strict';

angular.module('stepApp')
    .factory('EmployerSearch', function ($resource) {
        return $resource('api/_search/employers/:query', {}, {
            'query': {method: 'GET', isArray: true}
        });
    })
    .factory('EmployerByUserId', function ($resource) {
        return $resource('api/employers/user/:id', {}, {
            'query': {method: 'GET', isArray: true}
        });
    })
    .factory('ExistingEmployer', function ($resource) {
        return $resource('api/employers/:name', {}, {
            'query': {method: 'GET', isArray: false}
        });
    });
