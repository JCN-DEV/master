'use strict';

describe('DlBookIssue Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockDlBookIssue, MockInstEmployee, MockDlContTypeSet, MockDlContCatSet, MockDlContSCatSet, MockDlBookReturn;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockDlBookIssue = jasmine.createSpy('MockDlBookIssue');
        MockInstEmployee = jasmine.createSpy('MockInstEmployee');
        MockDlContTypeSet = jasmine.createSpy('MockDlContTypeSet');
        MockDlContCatSet = jasmine.createSpy('MockDlContCatSet');
        MockDlContSCatSet = jasmine.createSpy('MockDlContSCatSet');
        MockDlBookReturn = jasmine.createSpy('MockDlBookReturn');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'DlBookIssue': MockDlBookIssue,
            'InstEmployee': MockInstEmployee,
            'DlContTypeSet': MockDlContTypeSet,
            'DlContCatSet': MockDlContCatSet,
            'DlContSCatSet': MockDlContSCatSet,
            'DlBookReturn': MockDlBookReturn
        };
        createController = function() {
            $injector.get('$controller')("DlBookIssueDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:dlBookIssueUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
