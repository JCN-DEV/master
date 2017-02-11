'use strict';

describe('DlBookReturn Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockDlBookReturn, MockDlBookIssue, MockInstEmployee;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockDlBookReturn = jasmine.createSpy('MockDlBookReturn');
        MockDlBookIssue = jasmine.createSpy('MockDlBookIssue');
        MockInstEmployee = jasmine.createSpy('MockInstEmployee');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'DlBookReturn': MockDlBookReturn,
            'DlBookIssue': MockDlBookIssue,
            'InstEmployee': MockInstEmployee
        };
        createController = function() {
            $injector.get('$controller')("DlBookReturnDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:dlBookReturnUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
