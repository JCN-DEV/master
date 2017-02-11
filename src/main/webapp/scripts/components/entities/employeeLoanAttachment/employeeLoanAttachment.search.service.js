'use strict';

angular.module('stepApp')
    .factory('EmployeeLoanAttachmentSearch', function ($resource) {
        return $resource('api/_search/employeeLoanAttachments/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
