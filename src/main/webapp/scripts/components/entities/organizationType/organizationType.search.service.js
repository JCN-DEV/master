'use strict';

angular.module('stepApp')
    .factory('OrganizationTypeSearch', function ($resource) {
        return $resource('api/_search/organizationTypes/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }) .factory('OrganizationTypeActive', function ($resource) {
        return $resource('api/organizationTypes/active', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
