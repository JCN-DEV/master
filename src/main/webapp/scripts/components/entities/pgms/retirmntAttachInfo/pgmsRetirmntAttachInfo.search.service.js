'use strict';

angular.module('stepApp')
    .factory('PgmsRetirmntAttachInfoSearch', function ($resource) {
        return $resource('api/_search/pgmsRetirmntAttachInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
