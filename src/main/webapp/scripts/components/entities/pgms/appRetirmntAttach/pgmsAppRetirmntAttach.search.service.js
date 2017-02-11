'use strict';

angular.module('stepApp')
    .factory('PgmsAppRetirmntAttachSearch', function ($resource) {
        return $resource('api/_search/pgmsAppRetirmntAttachs/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
