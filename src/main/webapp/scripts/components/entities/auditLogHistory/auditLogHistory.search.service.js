'use strict';

angular.module('stepApp')
    .factory('AuditLogHistorySearch', function ($resource) {
        return $resource('api/_search/auditLogHistorys/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
