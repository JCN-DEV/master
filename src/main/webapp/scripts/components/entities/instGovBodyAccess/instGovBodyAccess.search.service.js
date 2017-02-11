'use strict';

angular.module('stepApp')
    .factory('InstGovBodyAccessSearch', function ($resource) {
        return $resource('api/_search/instGovBodyAccesss/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('InstGovBodyAccessCurrentInstitute', function ($resource) {
        return $resource('api/instGovBodyAccesss/currentInstitute', {}, {
            'get': { method: 'GET', isArray: false}
        });
    });
