'use strict';

angular.module('stepApp')
    .factory('JpExperienceCategorySearch', function ($resource) {
        return $resource('api/_search/jpExperienceCategorys/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
