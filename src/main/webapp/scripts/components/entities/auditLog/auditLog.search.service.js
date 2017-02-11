'use strict';

angular.module('stepApp')
    .factory('AuditLogSearch', function ($resource) {
        return $resource('api/_search/auditLogs/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
