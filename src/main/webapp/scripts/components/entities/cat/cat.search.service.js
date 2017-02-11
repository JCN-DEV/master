'use strict';

angular.module('stepApp')
    .factory('CatSearch', function ($resource) {
        return $resource('api/_search/cats/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('CatByOrganizationCat', function ($resource) {
        return $resource('api/cats/organizationCategory/:id', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('JobCategoriesWithCounter', function ($resource) {
        return $resource('api/cats/catsWithJobCounter', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
