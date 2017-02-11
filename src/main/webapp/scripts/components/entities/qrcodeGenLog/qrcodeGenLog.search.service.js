'use strict';

angular.module('stepApp')
    .factory('QrcodeGenLogSearch', function ($resource) {
        return $resource('api/_search/qrcodeGenLogs/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
