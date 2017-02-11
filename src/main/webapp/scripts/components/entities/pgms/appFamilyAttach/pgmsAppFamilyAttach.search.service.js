'use strict';

angular.module('stepApp')
    .factory('PgmsAppFamilyAttachSearch', function ($resource) {
        return $resource('api/_search/pgmsAppFamilyAttachs/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
