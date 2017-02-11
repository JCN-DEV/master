'use strict';

describe('DlExistingBookIssue Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockDlExistingBookIssue, MockDlExistBookInfo;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockDlExistingBookIssue = jasmine.createSpy('MockDlExistingBookIssue');
        MockDlExistBookInfo = jasmine.createSpy('MockDlExistBookInfo');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'DlExistingBookIssue': MockDlExistingBookIssue,
            'DlExistBookInfo': MockDlExistBookInfo
        };
        createController = function() {
            $injector.get('$controller')("DlExistingBookIssueDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:dlExistingBookIssueUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
