'use strict';

angular.module('stepApp')
    .factory('OrganizationCategorySearch', function ($resource) {
        return $resource('api/_search/organizationCategorys/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }) .factory('OrganizationCategoryActive', function ($resource) {
        return $resource('api/organizationCategorys/active', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
