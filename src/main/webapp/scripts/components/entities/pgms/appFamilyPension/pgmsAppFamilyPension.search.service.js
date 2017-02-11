'use strict';

angular.module('stepApp')
    .factory('PgmsAppFamilyPensionSearch', function ($resource) {
        return $resource('api/_search/pgmsAppFamilyPensions/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
