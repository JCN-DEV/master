'use strict';

angular.module('stepApp')
    .factory('UpazilaSearch', function ($resource) {
        return $resource('api/_search/upazilas/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('UpazilasByDistrict', function ($resource) {
        return $resource('api/upazilas/district/:id', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
