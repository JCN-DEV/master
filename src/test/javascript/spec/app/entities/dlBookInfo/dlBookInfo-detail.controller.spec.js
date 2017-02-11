'use strict';

describe('DlBookInfo Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockDlBookInfo, MockDlContTypeSet, MockDlContCatSet, MockDlContSCatSet;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockDlBookInfo = jasmine.createSpy('MockDlBookInfo');
        MockDlContTypeSet = jasmine.createSpy('MockDlContTypeSet');
        MockDlContCatSet = jasmine.createSpy('MockDlContCatSet');
        MockDlContSCatSet = jasmine.createSpy('MockDlContSCatSet');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'DlBookInfo': MockDlBookInfo,
            'DlContTypeSet': MockDlContTypeSet,
            'DlContCatSet': MockDlContCatSet,
            'DlContSCatSet': MockDlContSCatSet
        };
        createController = function() {
            $injector.get('$controller')("DlBookInfoDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:dlBookInfoUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
